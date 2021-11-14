exports.up = function (knex) {
    return knex.schema.createTable("favorites", (tbl) => {
        tbl.increments()
        tbl.integer('property_ad_id').notNullable().references('id').inTable('propertyAd')
            .onDelete('CASCADE').onUpdate('CASCADE')
        tbl.integer('user_id').notNullable().references('id').inTable('users')
            .onDelete('CASCADE').onUpdate('CASCADE')
    });
};

exports.down = function (knex) {
    return knex.schema.dropTableIfExists("favorites");
};
