# Registry for Docker

## Reference
- https://hub.docker.com/_/registry

## Docker deployment to the local workstation

~~~
# start the container
docker run -d -p 5000:5000 --name registry registry:2.7.1

# see the status
docker container ls

# destroy the container
docker container stop registry
docker container rm registry
~~~


## Kubernetes deployment to the local workstation (macOS only)

### Prep your local workstation (macOS only)
1. Clone this repo and work in it's root directory
1. Install Docker Desktop for Mac (https://www.docker.com/products/docker-desktop)
1. In Docker Desktop > Preferences > Kubernetes, check 'Enable Kubernetes'
1. Click on the Docker item in the Menu Bar. Mouse to the 'Kubernetes' menu item and ensure that 'docker-for-desktop' is selected.

Deploy (run these commands from the root folder of this repo)
~~~
mkdir -p /Users/Shared/Kubernetes/persistent-volumes/registry
kubectl apply -f ./kubernetes/registry-local-pv.yaml
kubectl apply -f ./kubernetes/registry.yaml
~~~

Delete
~~~
kubectl delete -f ./kubernetes/registry.yaml
kubectl delete -f ./kubernetes/registry-local-pv.yaml
rm -rf /Users/Shared/Kubernetes/persistent-volumes/registry


## For Basic Auth in the Docker Registry, we need to create a htpasswd. 
~~~
# generate a USERNAME
openssl rand -base64 15
ub99IW1CbEJMcOIjlvKK

# generate a PASSWORD	
openssl rand -base64 15
tnLbJuv4t1Yfs1nl6Hln

htpasswd -c auth ub99IW1CbEJMcOIjlvKK
New password: tnLbJuv4t1Yfs1nl6Hln
Re-type new password:
Adding password for user ub99IW1CbEJMcOIjlvKK

kubectl create secret generic basic-auth --from-file=auth

kubectl get secret basic-auth -o yaml

# copy the hash from that yaml

kubectl delete secret basic-auth
rm auth
~~~

Result goes into the auth: attribute in the deployment