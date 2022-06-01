const {Sequelize, DataTypes, Model} = require('sequelize')
const { sequelize } = require('./db')
const Dating = require('../models/dating.model')
const User = require('./user.model')
const Infos = sequelize.define('Info',{
    id: {
        type: DataTypes.UUID,
        defaultValue: DataTypes.UUIDV4,
        primaryKey: true
    },
    firstName: {
        type: DataTypes.STRING,
        allowNull: false
    },
    lastName: {
        type: DataTypes.STRING,
        allowNull: false
    },
    sexe: {
        type: DataTypes.STRING,
        allowNull: false
    },
    birthdate:{
        type: DataTypes.DATE,
        allowNull: false
    }
})

Infos.hasMany(Dating)
Dating.belongsTo(Infos)

module.exports = Infos
