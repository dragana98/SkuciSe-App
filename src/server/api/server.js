const express = require('express');
const usersRouter = require("../routes/users-routes");
const authRouter = require("../auth/auth-routes");
const restricted = require("../auth/restricted-middleware");

const server = express();
server.use(express.json());

server.get('/', (req, res) => {
    res.json({ message: 'I am son of Hal and am always watching!'})
});

server.use('/api/users', restricted, usersRouter);
server.use('/api/auth', authRouter);

module.exports = server;