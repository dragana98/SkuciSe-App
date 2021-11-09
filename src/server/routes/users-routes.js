const express = require('express');
const Users = require('../models/Users');
const router = express.Router();

//! FOR `USERS` ROUTES

router.get('/', (req, res) => {
    Users.readAll()
    .then( users => {
        res.status(200).json(users) 
    })
    .catch( error => {
        res.status(500).json({ mesage: "Server Error"})
    })
});

router.get('/:username', (req, res) => {
    const { username } = req.params;
    Users.read(username)
    .then( user => {
        res.status(200).json(user) 
    })
    .catch( error => {
        res.status(500).json({ mesage: "Server Error"})
    })
});

router.get('/:id/favorites', (req, res) => {
    const { id } = req.params;
    Users.getFavorites(id)
    .then( favs => {
        res.status(200).json(favs)
    })
    .catch( err => {
        res.status(200).json(err);
    })
});
module.exports = router;
