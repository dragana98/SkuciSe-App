const express = require('express');
const Realtors = require('../models/Realtors');
const router = express.Router();

router.get('/:id', (req, res) => {
    const { id } = req.params;
    Realtors.readByRealtorId(id)
        .then(user => {
            res.status(200).json(user)
        })
        .catch(error => {
            res.status(500).json({ message: "Server Error" })
        })
});

router.get('/legalEntity/:id', (req, res) => {
    const { id } = req.params;
    Realtors.readRealtorLegalEntitiyDataById(id)
        .then(realtor => {
            res.status(200).json(realtor)
        })
        .catch(error => {
            res.status(500).json({ message: "Server Error" })
        })
});


router.post('/', (req, res) => {
    const data = req.body;
    const { user_id, natural_person, name, corporate_address, website_url, avatar_url } = data

    if (!(user_id && (natural_person != undefined))) {
        res.status(400).json({ message: "Missing information" })
    } else if (natural_person == 0 && (!(name && corporate_address && website_url && avatar_url))) {
        res.status(400).json({ message: "Missing information" })
    } else {
        Realtors.create(data)
            .then(r => {
                res.status(200).json(r)
            })
            .catch(err => {
                res.status(200).json(err);
            })
    }

});
/* 
router.get('/:username/favorites', (req, res) => {
    const { username } = req.params;
    Users.getFavorites(username)
        .then(favs => {
            res.status(200).json(favs)
        })
        .catch(err => {
            res.status(200).json(err);
        })
}); */
module.exports = router;
