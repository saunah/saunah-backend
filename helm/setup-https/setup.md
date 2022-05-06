# Setup Traefik 2 with Cert-Manager to issue Certificate from Let's Encrypt

Source (sightly changed): [https://www.scaleway.com/en/docs/tutorials/traefik-v2-cert-manager/](https://www.scaleway.com/en/docs/tutorials/traefik-v2-cert-manager/)

1. Create new Kubernetes Kapsule on scaleway.com
2. Configure it to come with Traefix 2 pre-installed
3. Modify the default Traefik 2 daemonset running on Kapsule to do that, add `--entrypoints.websecure.http.tls` in the cmd stanza.

```
kubectl edit ds traefik -n kube-system
```

```
daemonset.apps/traefik edited
[]
        - --global.checknewversion
        - --global.sendanonymoususage
        - --entryPoints.traefik.address=:9000
        - --entryPoints.web.address=:8000
        - --entryPoints.websecure.address=:8443
        - --entrypoints.websecure.http.tls
        - --api.dashboard=true
        - --ping=true
        - --providers.kubernetescrd
        - --providers.kubernetesingress
[]
```

3. Delete the existing Traefik pods in order to get the new arguments.


```
kubectl -n kube-system delete pod -l app.kubernetes.io/name=traefik
```

4. Use the command below to install cert-manager and its needed CRD

```
kubectl apply -f https://github.com/cert-manager/cert-manager/releases/download/v1.8.0/cert-manager.yaml
```

5. Create a cluster issuer that allow you to specify
    - the Let’s Encrypt server, if you want to replace the production environment with the staging one.
    - the mail used by Let’s Encrypt to warn you about certificate expiration.
Copy and paste the following configuration in the file `cluster-issuer.yaml` using your favorite text editor:

```
apiVersion: cert-manager.io/v1
kind: ClusterIssuer
metadata:
  name: letsencrypt-staging
spec:
  acme:
    # You must replace this email address with your own.
    # Let's Encrypt will use this to contact you about expiring
    # certificates, and issues related to your account.
    email: saunah@protonmail.com
    server: https://acme-staging-v02.api.letsencrypt.org/directory
    privateKeySecretRef:
      # Secret resource used to store the account's private key.
      name: saunah-letsencrypt-staging
    # Add a single challenge solver, HTTP01
    solvers:
      - http01:
          ingress:
            class: traefik
---
apiVersion: cert-manager.io/v1
kind: ClusterIssuer
metadata:
  name: letsencrypt-prod
spec:
  acme:
    # You must replace this email address with your own.
    # Let's Encrypt will use this to contact you about expiring
    # certificates, and issues related to your account.
    email: saunah@protonmail.com
    server: https://acme-v02.api.letsencrypt.org/directory
    privateKeySecretRef:
      # Secret resource used to store the account's private key.
      name: saunah-letsencrypt-prod
    # Add a single challenge solver, HTTP01
    solvers:
      - http01:
          ingress:
            class: traefik
```

6. Use kubectl to apply the configuration

```
kubectl apply -f cluster-issuer.yaml
```

7. Create a edit a file `cert.yaml` as follows:

```
apiVersion: cert-manager.io/v1
kind: Certificate
metadata:
  name: saunah-cert
  namespace: default
spec:
  commonName: booking.saunah.ch
  secretName: saunah-cert
  dnsNames:
    - booking.saunah.ch
    - booking-backend.saunah.ch
  issuerRef:
    name: letsencrypt-prod #letsencrypt-staging
    kind: ClusterIssuer
```

8. Apply the configuration using kubectl:

```
kubectl apply -f cert.yaml
```

9. Check the certificate has been correctly created (you should see “Ready” in the condition):

```
kubectl describe certificate -n default saunah-cert
```

10. Add middleware to redirect from `http` to `https`, create a file `redirect-https.yaml`:

```
# Redirect to https
apiVersion: traefik.containo.us/v1alpha1
kind: Middleware
metadata:
  name: redirect-https
  namespace: default
spec:
  redirectScheme:
    scheme: https
    permanent: true
```

11. Apply `redirect-https.yaml`

```
kubectl apply -f redirect-https.yaml
```

12. Modify annotations in values file activate redirect from `http` to `https`

```
ingress:
  annotations:
    traefik.ingress.kubernetes.io/router.entrypoints: web,websecure
    traefik.ingress.kubernetes.io/router.middlewares: default-redirect-https@kubernetescrd # prefixed for namespace (default)
```

Note that the entrypoint *needs* to be both `web` and `websecure`, as otherwise the redirect from `http` to `https` will not work.

13. Modify ingress configuration in values file to use `saunah-cert`, by modifying ingress values:

```
ingress:
  tls:
    - secretName: saunah-cert
      hosts:
        - booking-backend.saunah.ch
```
