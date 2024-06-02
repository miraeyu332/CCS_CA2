const express = require('express');
const path = require('path');

const app = express();
const PORT = 8081;

// Serve static files from the "public" directory
app.use(express.static(path.join(__dirname, 'public')));

// Example API endpoint
app.get('/api/data', (req, res) => {
    const cellNumber = req.query.cell;
    res.json({ message: `You clicked on cell ${cellNumber}` });
});

app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, 'public', 'index.html'));
});

app.listen(PORT, () => {
    console.log(`Server is running on http://localhost:${PORT}`);
});