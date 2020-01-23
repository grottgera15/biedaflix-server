#!/usr/bin/env bash

while getopts ":i:" opt; do
    case $opt in
        i)
            filePath=${OPTARG}
            ;;
           \?)
            echo "Invalid option: -$OPTARG" >&2
            ;;
        :)
            echo "Option -$OPTARG requires a path to file."
            exit 1
            ;;
    esac
done

if [[ ! -r "$filePath" ]]; then
    echo "You need to specify proper file path with -i flag."
    exit 1
fi

find  "$filePath" -depth -name "* *" -execdir rename "s/ /_/g" *  {} \;