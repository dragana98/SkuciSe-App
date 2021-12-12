const express = require('express');
const Favorites = require('../models/Favorites');
const router = express.Router();

router.get('/', (req, res) => {
    const username = req.decodedToken.username;

    Favorites.read(username)
        .then(fav => {
            res.status(200).json(fav)
        })
        .catch(error => {
            res.status(500).json({ mesage: "Server Error" })
        })
});

router.post('/', (req, res) => {
    var data = req.body;
    
    const { property_ad_id} = data
    data.username = req.decodedToken.username;
    
    if (! property_ad_id) {
        res.status(400).json({ message: "Missing information" })
    } else {
        Favorites.create_del(data)
            .then(fav => {
                res.status(200).json(fav);
            })
            .catch(err => {
                console.log(err);
                res.status(500).json(err);
            })
    }
});

module.exports = router;