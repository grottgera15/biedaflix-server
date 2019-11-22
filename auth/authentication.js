const passport = require("passport");
const User = require("../models/user");
const UserController = require("../controllers/user");

let localStrategy = require("passport-local").Strategy;

passport.use(
    new localStrategy(
        { usernameField: "identification", passwordField: "password" },
        (identification, password, done) => {
            User.findOne({ $or: [{ name: identification }, { email: identification }] }, function (
                err,
                user
            ) {
                if (err) {
                    return done(err);
                }
                if (!user) {
                    return done(null, false);
                }
                UserController.comparePassword(password, user.password, function (err, isMatch) {
                    if (err) throw err;
                    if (isMatch) {
                        return done(null, user);
                    } else {
                        return done(null, false, { message: "Invalid password" });
                    }
                });
                return done(null, user);
            });
        }
    )
);

passport.serializeUser((user, done) => {
    done(null, user.id);
});

passport.deserializeUser((id, done) => {
    User.findById(id, (err, user) => {
        done(err, user);
    });
});