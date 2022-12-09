export MYSQLDB_ROOT_PASSWORD := root
export MYSQLDB_DATABASE := school
export MYSQLDB_LOCAL_PORT := 3306
export MYSQLDB_DOCKER_PORT := 3306

run_db :
	docker-compose -f db-test.yaml up -d
stop_db :
	docker-compose -f db-test.yaml down
