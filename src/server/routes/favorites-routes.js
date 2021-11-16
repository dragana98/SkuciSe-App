const express = require('express');
const Favorites = require('../models/Favorites');
const router = express.Router();

router.get('/:data', (req, res) => {
    // TODO dynamically add/remove favorite
    const { user_id } = req.params;
    Favorites.read(user_id)
        .then(fav => {
            res.status(200).json(fav)
        })
        .catch(error => {
            res.status(500).json({ mesage: "Server Error" })
        })
});

router.post('/', (req, res) => {
    const data = req.body;
    const { property_ad_id, user_id} = data
    if (! (property_ad_id && user_id)) {
        res.status(400).json({ message: "Missing information" })
    } else {
        Favorites.create(data)
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