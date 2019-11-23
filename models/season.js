const mongoose = require("mongoose");
const EpisodeSchema = require("./episode");

let SeasonSchema = new mongoose.Schema({
    seasonNumber: { type: Number },
    episodes: [EpisodeSchema]
})

module.exports = SeasonSchema;