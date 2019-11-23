const mongoose = require("mongoose");
const SeasonSchema = require("./season");


let SeriesSchema = new mongoose.Schema({
    info: {
        name: { type: String },
        description: { type: String },
        bannerVideo: { type: String },
        logo: { type: String },
        source: { type: String },
        onGoing: { type: Boolean }
    },
    seasons: [SeasonSchema]
}, {
    autoCreate: true
});

let Series = mongoose.model("Series", SeriesSchema, "Series");

let test = new Series();
test.save();

module.exports = Series;

