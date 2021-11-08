const express = require('express');
const session = require('express-session');
const usersRouter = require("../routes/users-routes");
const authRouter = require("../auth/auth-routes");
const restricted = require("../auth/restricted-middleware");

const server = express();
const sessionConfig = {
    name: 'monster',
    secret: process.env.SECRET,
    cookie: {
        maxAge: 1000 * 60 * 60, //ms
        secure: false, //https 
        httpOnly: true, //no access from script
    },
    resave: false,
    saveUninitialized: true, //GDPR laws
}

server.use(session(sessionConfig))
server.use(express.json());

server.get('/', (req, res) => {
    res.json({ message: 'I am son of Hal and am always watching!'})
});

server.use('/api/users', restricted, usersRouter);
server.use('/api/auth', authRouter);

module.exports = server;