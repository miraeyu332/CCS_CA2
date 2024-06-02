document.addEventListener("DOMContentLoaded", function () {
    const board = document.getElementById("gameBoard");

    // Create a 10x10 board
    for (let i = 0; i < 100; i++) {
        const cell = document.createElement("div");
        cell.className = "cell";
        cell.addEventListener("click", () => cellClicked(i));
        board.appendChild(cell);
    }

    function cellClicked(index) {
        console.log(`Cell ${index} clicked`);
        // Add more logic here for what happens when a cell is clicked
    }
});