### Install Pod Using Helm
```bash
helm install stable/postgresql
```

### Set Password In Local Environment
```bash
$ export POSTGRES_PASSWORD=$(kubectl get secret --namespace default postgres-postgresql -o jsonpath="{.data.postgresql-password}" | base64 --decode)
```

### Run A Client
```bash
$ kubectl run postgres-postgresql-client --rm --tty -i --restart='Never' --namespace default --image docker.io/bitnami/postgresql:10.7.0 --env="PGPASSWORD=$POSTGRES_PASSWORD" --command -- psql --host postgres-postgresql -U postgres
```

### Access And Populate Database
```bash
$ kubectl port-forward --namespace default svc/yodeling-serval-postgresql 5432:5432 &
$ PGPASSWORD="$POSTGRES_PASSWORD" psql --host 127.0.0.1 -U postgres
postgres# CREATE DATABASE test;
postgres# \connect test;
postgres# CREATE TABLE data (id SERIAL PRIMARY KEY, value VARCHAR (50) NOT NULL);
postgres# INSERT INTO data (value) VALUES ('some data');
postgres# INSERT INTO data (value) VALUES ('some other data');
```
