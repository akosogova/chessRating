db = db.getSiblingDB('ChessRating');
db.players.insertMany([
    {_id: 'd226a389-e2a3-41fd-80ec-f5965ba6cb96', firstName: 'User', lastName: 'Testuser', email: 'test.user@gmail.com', chessData: { rating: 1700 }, _class: 'com.chessrating.model.Player'},
    {_id: '6bfeb8d5-13ff-4199-a152-7fdb68df853b', firstName: 'Manager', lastName: 'Testmanager', email: 'test.manager@gmail.com', chessData: { rating: 1600 }, _class: 'com.chessrating.model.Player'},
    {_id: '12345678-ab80-4538-b6ef-2db5fdf6e905', firstName: 'Insha', lastName: 'Cat', email: 'insha.cat@gmail.com', chessData: { rating: 1500 }, _class: 'com.chessrating.model.Player'},
]);
