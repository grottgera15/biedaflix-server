const mongoose = require("mongoose");

let EpisodeSchema = new mongoose.Schema({
    episodeNumber: { type: Number },
    name: { type: String },
    available: { type: Boolean },
    releaseDate: { type: Date },
    sources: {
        videos: [{
            quality: { type: String },
            path: { type: String }
        }],
        subtitles: [{
            language: { type: String },
            path: { type: String }
        }]
    }
})

module.exports = EpisodeSchema;