const express = require('express');
const app = express();
const port = 8082;

app.use(express.static('public'));  // Serve static files from 'public' directory

app.listen(port, () => {
    console.log(`Server running at http://localhost:${port}/`);
});