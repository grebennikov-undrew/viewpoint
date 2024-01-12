#!/bin/bash

echo LOAD SAMLPES FLAG: $LOAD_SAMPLES

if [ "$LOAD_SAMPLES" = "true" ]; then

    export PGPASSWORD="$POSTGRES_PASSWORD"
    echo ==== START LOAD SAMLPES ====

    # Загрузка данных из дампа
    createdb -h viewpoint-db -p $POSTGRES_PORT -U $POSTGRES_USER dvdrental
    pg_restore -h viewpoint-db -d dvdrental -p $POSTGRES_PORT -U $POSTGRES_USER ./docker-entrypoint-initdb.d/sample_data.tar

    # Загрузка метаданных BI
    echo ==== END LOAD SAMLPES ====

    # Активация tcp/ip доступа для БД с примерами
    
    # POSTGRES_CONF="/var/lib/postgresql/data/postgresql.conf"
    # PG_HBA_CONF="/var/lib/postgresql/data/pg_hba.conf"

    # ALLOWED_IP="0.0.0.0"

    # # Открываем доступ к TCP/IP-соединениям в postgresql.conf
    # sed -i "s/^#listen_addresses = 'localhost'/listen_addresses = '*'/" "$POSTGRES_CONF"
    # # Разрешаем соединения из ALLOWED_IP в pg_hba.conf
    # echo "host    all             all             $ALLOWED_IP/32      md5" >> "$PG_HBA_CONF"

    # # Перезапускаем PostgreSQL
    # systemctl restart postgresql

fi
