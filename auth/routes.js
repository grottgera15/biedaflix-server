const express = require("express");
const passport = require("passport");

let router = express.Router();

router.post("/login", passport.authenticate("local"), (req, res) => {
    res.send(req.user);
});

router.get("/logout", (req, res) => {
    req.logout();

    res.status(200).clearCookie("connect.sid", { path: "/", domain: ".gdziezagram.pl" });

    req.session.destroy(function(err) {
        if (err) throw err;

        res.send();
    });
});

module.exports = router;