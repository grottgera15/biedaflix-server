const express = require("express");
const bodyParser = require("body-parser");
const passport = require("passport");
const session = require("express-session");
const cors = require("cors");

const fs = require("fs");
//ensure all models are loaded before connection to mongodb is opened
let models_path = __dirname + "/models";
fs.readdirSync(models_path).forEach(function (file) {
    if (~file.indexOf(".js")) require(models_path + "/" + file);
});

const db = require("./database");

const app = express();
const server = require("http").Server(app);
//const io = require("./socketServer").initialize(server);

//app.set("socketio", io);

port = process.env.PORT || 8080;

app.use(express.json());
app.use(bodyParser.urlencoded({ extended: true }));

//cors
app.use(function (req, res, next) {
    res.header("Access-Control-Allow-Credentials", true);
    res.header("Access-Control-Allow-Origin", req.headers.origin);
    res.header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,UPDATE,OPTIONS");
    res.header(
        "Access-Control-Allow-Headers",
        "X-Requested-With, X-HTTP-Method-Override, Content-Type, Accept"
    );
    next();
});
app.use(
    cors({
        origin: "http://gdziezagram.pl",
        credentials: true
    })
);

//express session
app.use(
    session({
        secret: "secret",
        saveUninitialized: false,
        resave: true,
        domain: ".gdziezagram.pl",
        cookie: {
            maxAge: 3600 * 1000,
            domain: ".gdziezagram.pl",
            path: "/",
            httpOnly: false,
            secure: false
        }
    })
);

app.use(passport.initialize());
app.use(passport.session());

require("./auth/authentication");

server.listen(port, () => {
    console.log("server started on port: " + port);
});