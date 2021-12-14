const express = require('express');
const Users = require('../models/Users');
const router = express.Router();

//! FOR `USERS` ROUTES

/* router.get('/', (req, res) => {
    Users.readAll()
    .then( users => {
        res.status(200).json(users) 
    })
    .catch( error => {
        res.status(500).json({ message: "Server Error"})
    })
}); */

router.get('/', (req, res) => {
    const username = req.decodedToken.username;
    Users.readData(username)
        .then(user => {
            res.status(200).json(user)
        })
        .catch(error => {
            res.status(500).json({ message: "Server Error" })
        })
});

router.get('/basic/:id', (req, res) => {
    const { id } = req.params;
    Users.readBasicData(id)
        .then(user => {
            res.status(200).json(user)
        })
        .catch(error => {
            res.status(500).json({ message: "Server Error" })
        })
});

// TODO check if the new username already exists in the database
// TODO if password changed, logout and invalidate token(s)

router.post('/update/', (req, res) => {
    var data = req.body;

    const { username, phone_number, password, avatar_url } = data

    if (username) {
        data.username = req.decodedToken.username;
    }
    if (phone_number) {
        data.phone_number = null
    }

    if (password) {
        data.password = null
    }

    if (avatar_url) {
        data.avatar_url = null;
    }

    data.old_username = req.decodedToken.username;
    Users.update(data)
        .then(user => {
            res.status(200).json(user)
        })
        .catch(error => {
            res.status(500).json({ message: "Server Error" })
        })
});

module.exports = router;