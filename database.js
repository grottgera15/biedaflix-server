const mongoose = require("mongoose");

let password = "admin123";

let localUri = encodeURI("mongodb://database:27017");

mongoose.connect(
    localUri,
    { user: 'admin', pass: password, useNewUrlParser: true, connectTimeoutMS: 1000, useCreateIndex: true },
    error => {
        console.log("response");
        if (error) {
            throw error;
        }
    }
);

const db = mongoose.connection;

db.on("error", console.error.bind(console, "connection error:"));

db.on("open", () => {
    console.log("connection opened");
});

db.once("connected", function (error) {
    console.log("connected to atlas server");
});

mongoose.set("useFindAndModify", false);

module.exports = db;
