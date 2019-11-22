const User = require("../models/user");
const bcrypt = require("bcryptjs");

module.exports.GetUsers = function(query = {}, options = {}, callback) {
    User.paginate(query, options, callback);
};

module.exports.getUserByName = function(name, callback) {
    User.findOne({ name: name }, callback);
};

module.exports.getUserByID = function(id, callback) {
    User.findById(id, callback);
};

module.exports.deleteUserByID = function(id, callback) {
    User.findByIdAndDelete(id, callback);
};

module.exports.updateUserByID = function(id, changes, callback) {
    User.findByIdAndUpdate(id, changes, { new: true }, callback);
};

module.exports.createUser = function(user, callback) {
    bcrypt.hash(User.password, 10, (err, hash) => {
        user.password = hash;
        user.save(callback);
    });
};

module.exports.comparePassword = function(password, hash, callback) {
    bcrypt.compare(password, hash, function(err, isMatch) {
        if (err) throw err;
        callback(null, isMatch);
    });
};