# Deploy API Application To Google Cloud Registry

Build The App:
--
```bash
$ ./gradlew build docker
```

Configure Local Docker Registry:
--
```bash
$ gcloud auth configure-docker
```
Tag The Docker Image:
--
```bash
# Grab the image id from this list
$ docker images --all
$ docker tag <image id> us.gcr.io/enfuse-gke/<image name to appear in google registry>
```

Push To Registry:
--
```bash
$ docker push us.gcr.io/enfuse-gke/<image name>
```

Deploy The Image To GKE:
--
```bash
kubectl run <name the GKE deployment> --image=us.gcr.io/enfuse-gke/<image name>--port=8080
```

Expose Image As A Service:
--
```bash
 kubectl expose deployment <name of GKE deployment> --type=LoadBalancer --name=<name the service>
```