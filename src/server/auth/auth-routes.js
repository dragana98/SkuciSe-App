const express = require('express');
const Users = require('../models/Users');
const router = express.Router();
const bcrypt = require('bcryptjs');
const generateToken = require('./generateToken');

router.post('/register', (req, res) => {
    const credentials = req.body;
    const { username, password, name, surname, date_of_birth, phone_number, document_type, document_number, avatar_url } = credentials;

    if (!(username && password && name && surname && date_of_birth && phone_number && document_type && document_number && avatar_url)) {
        res.status(400).json({ message: "Missing information" })
    }
    else {
        const hash = bcrypt.hashSync(credentials.password, 12);
        credentials.password = hash;

        Users.create(credentials)
            .then(user => {
                res.status(200).json(user)
            })
            .catch(err => {
                if (err)
                    res.status(500).json(err.message)
            })
    }
});

router.post('/login', (req, res) => {
    const { username, password } = req.body;

    if (!(username && password)) {
        res.status(400).json({ message: "Missing information" })
    } else {
        Users.readCred(username)
            .then(user => {
                if (user && bcrypt.compareSync(password, user['password'])) {
                    const token = generateToken(user);
                    res.status(200).json({ id: user.id, token: token })
                } else {
                    res.status(401).json({ message: 'Invalid credentials' })
                }
            })
            .catch(error => {
                console.log(error);
                res.status(500).json(error)
            })
    }
});

module.exports = router;