
const mongoose = require("mongoose");

let UserSchema = new mongoose.Schema(
    {
        email: { type: String, unique: true, required: true },
        password: { type: String, required: true, select: false },
        isValid: { type: Boolean, default: false }
    },
    { autoCreate: true }
);

const emailValidator = require("email-validator");

UserSchema.path("email").validate(
    v => {
        return emailValidator.validate(v);
    },
    `not an email`,
    `invalid email`
);


let User = mongoose.model("User", UserSchema);

module.exports = User;