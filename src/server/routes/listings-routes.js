const express = require('express');
const Listings = require('../models/Listings');
const router = express.Router();

router.get('/', (req, res) => {
    Listings.readAll()
    .then( realties => {
        res.status(200).json(realties)
    })
    .catch( err => {
        console.log(err);
        res.status(500).json(err)
    })
});

router.get('/:id', (req, res) => {
    const { id } = req.params;
    Listings.read(id)
    .then( realty => {
        res.status(200).json(realty);
    })
    .catch( err => {
        res.status(500).json(err);
    })
});

router.post('/', (req, res) => {
    const data = req.body;

    Listings.create(data)
    .then( realty => {
        res.status(200).json(realty);
    })
    .catch( err => {
        console.log(err);
        res.status(500).json(err);
    })
});

router.delete('/:id', (req, res) => {
    const { id } = req.params;

    Listings.del(id)
    .then( num => {
        res.status(200).json(num);
    })
    .catch( err => {
        console.log(err);
        res.status(500).json(err);
    })
});

router.post('/:id/review', (req, res) => {

    const { id } = req.params;
    const body = req.body;

    Listings.leaveReview(id, body)
    .then( id => {
        res.status(200).json(id)
    })
    .catch()
})

router.get('/:id/review', (req, res) => {

    const { id } = req.params;
    Listings.getReview(id)
    .then( review => {
        res.status(200).json(review)
    })
    .catch( err => {
            res.status(500).json(err)
        }
    )
})
module.exports = router;