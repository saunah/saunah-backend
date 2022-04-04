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

## Installing Secrets

Before installing any other deployments to the cluster, install secrets.
A template (without actual values) can be found in `/helm/secrets-template.yaml`. Rename this file to `helm/secrets.yaml` and insert the corresponding values.

To apply the configuration, run

```shell
kubectl apply -f secrets.yaml
```

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


## Removing other resources

To remove other resources, run

```shell
kubectl delete pod name
kubectl delete pod name
kubectl delete pod name
kubectl delete pod name
kubectl delete pod name
...
```

where `name` should be replaced with the actual name of the resource to delete, which can be retrieved by listing the resources as shown above.
