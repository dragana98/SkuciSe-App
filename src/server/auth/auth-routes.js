const express = require('express');
const Users = require('../models/Users');
const router = express.Router();
const bcrypt = require('bcryptjs');
const generateToken = require('./generateToken');

router.post('/register', (req, res) => {
    const credentials = req.body;
    const { username, password } = credentials;

    if( !(username && password) ){
        res.status(400).json({message: "Missing information"})
    }

    const hash = bcrypt.hashSync(credentials.password, 12);
    credentials.password = hash;

    Users.create(credentials)
    .then( user => {
        res.status(200).json(user)
    })
    .catch( error => {
        if(error.errno == 19)
            res.status(418).json({ message: "I'm a teapot"})
    })
});

router.post('/login', (req, res) => {
    const { username, password } = req.body;

    if(!(username && password)){
        res.status(400).json({message: "Missing information"})
    }else{
        Users.read(username)
        .then( user => {
            if(user && bcrypt.compareSync(password, user['password'])){
               const token = generateToken(user);

                res.status(200).json({ message: `Welcome ${user.username}`, token})
            }else{
                res.status(401).json({ message: 'Invalid credentials' })
            }
        })
        .catch( error => {
            console.log(error);
            res.status(500).json(error)
        })
    }
});

router.get('/logout', (req, res) => {
    if(req.session){
        req.session.destroy(error => {
            if(error){
                res.statuts(500).jso({ message: "You may check out anytime you like, but you may never leave"});
            }else{
                res.status(200).json({message: "Successfully logged out"})
            }
        });
    }else{
        res.status(200).json({ message: "Session not found"});
    }
})
module.exports = router;