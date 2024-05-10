#!/bin/bash
# wait-for-it.sh

host="$1"
port="$2"
timeout="${3:-60}"
cmd="${@:4}"

echo "Waiting for $host:$port to become available..."

start_ts=$(date +%s)
while :
do
  nc -z "$host" "$port" >/dev/null 2>&1
  result=$?
  if [ $result -eq 0 ]; then
    end_ts=$(date +%s)
    echo "$host:$port is available after $((end_ts - start_ts)) seconds."
    break
  fi
  current_ts=$(date +%s)
  if [ $((current_ts - start_ts)) -gt $timeout ]; then
    echo "Timeout exceeded while waiting for $host:$port to become available."
    exit 1
  fi
  sleep 1
done

exec "$cmd"