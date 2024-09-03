FROM postgres

ENV POSTGRES_PASSWORD postgres

COPY ./init/init.sql /docker-entrypoint-initdb.d/
