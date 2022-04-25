# Kubernetes Deployment Notes Backend

Some notes for reference on commands needed to deploy the application to a kubernetes cluster.


## Setting Local kubectl Context

In order to configure `kubectl` command-line tool, make sure the correct context is active.

Switch context with

```shell
kubectl config use-context minikube
```

where `minikube` is the name of the context to switch to.

Make also sure that the namespace is set in the kubeconfig file (found in `~/.kube/config`)

A sample kubeconfig file can be found in [`kubeconfig-example`](./kubeconfig-example). Modify it accordingly, rename it to `config` and place it at `~/.kube/config`.

## Installing Secrets

Before installing any other deployments to the cluster, install secrets.
A helm chart which contains a template for installing the needed secrets can be found at `helm/saunah-secrets`.

To apply the chart, run

```shell
helm upgrade --install -f helm/saunah-secrets/values-local.yaml saunah-secrets helm/saunah-secrets
```

where `values-local.yaml` should be replaced with the values file inteneded for the deployment.

An example for the values file can be found at `helm/saunah-secrets/values-example.yaml`.

***Note*** that secrets need to be base64 encoded (**with no trailing newline char!!**).
To get base64 value, run

```shell
echo -n 'yoursecret' | base64
```

where `yoursecret` is the actual secret. Note the `-n` parameter in the echo command, which tells echo to not add a newline (`\n`) at the end of the string echoed (which would be the default behaviour).

## Deploying the Database

Make sure the secrets have been deployed successfully. Otherwise, deployment of the database will fail.

To deploy the database, run

```shell
helm upgrade --install -f helm/saunah-database/values-local.yaml saunah-database helm/saunah-database
```

where `values-local.yaml` should be replaced with the values file inteneded for the deployment.


## Deploying the Backend

To deploy the backend, run

```shell
helm upgrade --install -f helm/saunah-backend/values-local.yaml saunah-backend helm/saunah-backend
```

where `values-local.yaml` should be replaced with the values file intended for the deployment.


## Listing Helm Charts

To list currently installed helm charts, run

```shell
helm list
```

## Removing a Helm Chart

To remove an installed helm chart, run

```
helm delete saunah-backend
```

where `saunah-backend` should be replaced with the actual name of the application which should be deleted.


## Listing Other Resources

To list other resources active on a cluster, run

```
kubectl get pods
kubectl get deployments
kubectl get secrets
kubectl get pvc
kubectl get pv
...
```


## Removing Other Resources

To remove other resources, run

```shell
kubectl delete pod name
kubectl delete deployment name
kubectl delete secret name
kubectl delete pvc name
kubectl delete pv name
...
```

where `name` should be replaced with the actual name of the resource to delete, which can be retrieved by listing the resources as shown above.
