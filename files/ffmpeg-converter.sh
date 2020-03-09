#!/bin/bash

get_duration () {
    local result=$(ffprobe -v error -show_entries format="duration" -of default="noprint_wrappers=1:nokey=1" "$1")
    echo "$result"
}

detect_crop () {
    ffmpeg -ss "$2" -i "$1" -t 15 -vf "cropdetect=24:2:0" -f null - 2> "$3/output.txt"
    local result=$(grep -o -m 1 "crop=.*" "$3"/output.txt)
    echo "$result"
}

crf=21

while getopts ":i:n:d:e:" opt; do
    case $opt in
        i)
            filePath=${OPTARG}
            ;;
        n)
            series=${OPTARG}
            ;;
        d)
            destinationPath=${OPTARG}
            ;;
        e)
            episode=${OPTARG}
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

if [[ -z "$series" ]]; then
    echo "You need to specify series id with -n flag."
    exit 1
fi

if [[ -z "$episode" ]]; then
    echo "You need to episode id with -e flags."
    exit 1
fi

if [[ ! -d "$destinationPath" ]]; then
    echo "You need to specify destination path for series with -d flag!"
    exit 1
fi

duration=$(get_duration "$filePath")
duration=$(bc<<<"$duration / 1")
durationCenter=$(bc<<<"$duration / 2")

if [[ ! -d "$destinationPath/$series" ]]; then
    mkdir -p "$destinationPath/$series"
fi

finalPath="$destinationPath/$series/$episode"

if [[ ! -d "$finalPath" ]]; then
    mkdir -p "$finalPath"
fi

crop=$(detect_crop "$filePath" "$durationCenter" "$finalPath")

mkdir -p "$finalPath/thumbs"

ffmpeg -i "$filePath" -vcodec libx264 -crf $crf -preset superfast -tune film -vf "$crop" "$finalPath/1080.mp4"
ffmpeg -i "$finalPath/1080.mp4" -vcodec libx264 -crf $((crf-1)) -preset superfast -tune film -vf scale=1280:-1 "$finalPath/720.mp4"
ffmpeg -i "$finalPath/1080.mp4" -vcodec libx264 -crf $((crf-3)) -preset superfast -tune film -vf scale=854:-1 "$finalPath/480.mp4"
ffmpeg -i "$finalPath/1080.mp4" -vf "scale=326:-1, fps=1/10" -crf 30 "$finalPath/thumbs/thumb%04d.jpg"

exit 0
