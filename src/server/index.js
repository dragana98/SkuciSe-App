const express = require('express');
const server = express();
const PORT = 8080;
const Database = require('./models/dbHelpers');
server.use(express.json());

server.get('/userCredentials', (req, res) => {
    // res.json({ message: 'I am son of Hal and am always watching!'})
    Database.read('userCredentials', 'all')
    .then( users => {
        res.status(200).json(users)
    })
    .catch(error => {
        res.status(500).json({ message: "server error"})
    })
});
server.post('/userCredentials', (req, res) => {
    Database.create('userCredentials', req.body)
    .then(userCredential => {
        res.status(200).json(userCredential)
    })
    .catch(error => {
        res.status(500).json({ message: "Server Error"})
    })
});
server.get('/userData', (req, res) => {
    Database.read('userData', 'all')
    .then( users => {
        res.status(200).json(users)
    })
    .catch(error => {
        res.status(500).json({ message: "server error"})
    })
});
server.get('/userData/:id', (req, res) => {
    const {id} = req.params;
    Database.read('userData', '', id)
    .then( users => {
        res.status(200).json(users)
    })
    .catch(error => {
        res.status(500).json({ message: "server error"})
    })
});
server.post('/userData', (req, res) => {
    Database.create('userData', req.body)
    .then(userData => {
        res.status(200).json(userData)
    })
    .catch(error => {
        res.status(500).json({ message: "Server Error"})
    })
});
server.delete('/userData/:id', (req, res) => {
    const { id } = req.params;
    Database.del('userData', '', id)
    .then(userData => {
        res.status(200).json(userData)
    })
    .catch(error => {
        res.status(500).json({ message: "Server Error"})
    })
});
server.delete('/userCredentials/:id', (req, res) => {
    const { id } = req.params;
    Database.del('userCredentials', '', id)
    .then(userData => {
        res.status(200).json(userData)
    })
    .catch(error => {
        res.status(500).json({ message: "Server Error"})
    })
});
server.listen(PORT, () => {
    console.log(`\n*** Server running on PORT:${PORT} ***\n`)
});

