MAKEFLAGS += --always-make

all:

jar:
	clj -M:uberjar

clean:
	rm -rf target/*

run:
	java -cp target/ignite-repl.jar clojure.main -m ignite_repl.core

repl:
	clj -Sdeps '{:deps {nrepl/nrepl {:mvn/version "0.8.3"}}}' -m nrepl.cmdline --connect --host localhost --port 3333

docker:
	docker build -t ignite-repl .

local_env_up:
	docker-compose up -d

local_env_down:
	docker-compose down
