# GCloud Setup

#### Helpful quickstart info:
* [Google Docs Quickstart](https://cloud.google.com/sdk/docs/quickstart-macos)

#### Download google-cloud-sdk:
* [macOS 64bit](https://dl.google.com/dl/cloudsdk/channels/rapid/downloads/google-cloud-sdk-242.0.0-darwin-x86_64.tar.gz)

#### Install:
```bash
$ ./google-cloud-sdk/install.sh
```
If gcloud is not in your path:
```bash
$ source ~/.bash_profile
```

#### Initialize/Authenticate:
This will open your browser for login to your GCP account
```bash
$ gcloud init
```

#### Setup kubectl:
```bash
$ gcloud components install kubectl
```
Uninstall your existing kubectl if you see warnings about version clash
```bash
$ brew uninstall kubernetes-cli
```

#### Get Credentials
```bash
$ gcloud container clusters list
$ gcloud container clusters get-credentials enfuse-kafka-connect-gke --zone us-central1-a
```