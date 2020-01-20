#! /bin/bash

get_duration () {
    local result=$(ffprobe -v error -show_entries format="duration" -of default="noprint_wrappers=1:nokey=1" $1)
    echo $result
}

detect_crop () {
    ffmpeg -ss $2 -i $1 -t 15 -vf "cropdetect=24:2:0" -f null - 2> output.txt
    local result=$(grep -o -m 1 "crop=.*" ./output.txt)
    echo $result
}

crf=21

while getopts ":i:a:n:s:d:e:" opt; do
    case $opt in
        i)
            filePath=${OPTARG}
            ;;
        a)
            crf=22
            ;;
        n)
            seriesName=${OPTARG}
            ;;
        d)
            destinationPath=${OPTARG}
            ;;
        s)
            season=${OPTARG}
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

if [[ -z "$seriesName" ]]; then
    echo "You need to specify series name with -n flag."
    exit 1
fi

if [[ -z "$season" ]] || [[ -z "$episode" ]]; then
    echo "You need to specity season and episode number with -s and -e flags."
    exit 1
fi

if [[ ! -d "$destinationPath" ]]; then
    echo "You need to specify destination path for your episode with -d flag!"
    exit 1
fi

duration=$(get_duration $filePath)
duration=$(bc<<<"$duration / 1")
durationCenter=$(bc<<<"$duration / 2")
crop=$(detect_crop $filePath $durationCenter)

if [[ ! -d "./s$season" ]]; then
    mkdir -p "./s$season"
fi

if [[ ! -d "./s$season/e$episode" ]]; then
    mkdir -p "./e$episode"
fi

finalPath="$destinationPath/s$season/e$episode"
mkdir -p "$finalPath/thumbs"

ffmpeg -i $filePath -vcodec libx264 -crf 21 -preset superfast -tune film -vf $crop "$finalPath/1080.mp4"
ffmpeg -i "$finalPath/1080.mp4" -vcodec libx264 -crf $crf -preset superfast -tune film -vf scale=1280:-1 "$finalPath/720.mp4"
ffmpeg -i "$finalPath/1080.mp4" -vcodec libx264 -crf (($crf-2)) -preset superfast -tune film -vf scale=854:-1 "$finalPath/480.mp4"
ffmpeg -i "$finalPath/1080.mp4" -vf "scale=326:-1, fps=1/10" -crf ((30)) "$finalPath/thumbs/thumb%04d.jpg"

exit 0  