const wordle = 5;
const chance = 6;
let random = Math.floor(Math.random() * (words.length - 1));
let puzzle = words[random];  //生成随机单词
console.log(puzzle);
let times = 1;  //当前第几次猜，也对应第几行，times-1对应tiles数组前一维度的下标
let index = 0;  //一行对应中第几个格子，对应tiles数组后一维度的下标
let tiles = []; //对应填写格，二维数组
let got = false; //是否猜对

window.handleLetterClick = (event) => {
  if (got || times > chance || index == wordle) return;
  const letter = event.target.innerText;
  console.log(letter);
  tiles[times-1] = tiles[times-1] || [];
  tiles[times-1][index] = letter;
  document.getElementById(times.toString() + index.toString()).innerText = letter;
  index += 1;
};

window.handleBackClick = (event) => {
  if (got || times > chance || index == 0) return;
  index -= 1;
  document.getElementById(times.toString() + index.toString()).innerText = "";
};

window.handleGuessClick = (event) => {
  if (got || times > chance || index != wordle) return;
  const word = tiles[times-1].join("");
  console.log(word);
  if (words.indexOf(word.toLowerCase()) == -1) {
    alert("not a word!");
    return;
  }
  if (puzzle == word.toLowerCase()) {
    got = true;
    alert("you got it!");
  } 
  //bug   puzzle:wreak  guess:greet  cause: second e is yellow, second e should be gray
  // tiles[times-1].forEach((letter, i) => {
  //   const j = puzzle.indexOf(letter.toLowerCase());
  //   if (j == -1) {
  //     document.getElementById(times.toString() + i.toString()).style.backgroundColor = "#787c7e";
  //     document.getElementById(times.toString() + i.toString()).style.color = "#fff";
  //     document.getElementById(letter).style.backgroundColor = "#787c7e";
  //     document.getElementById(letter).style.color = "#fff";
  //   } else {
  //     if (puzzle[i] == letter.toLowerCase()) {   // if (j == i) {
  //       document.getElementById(times.toString() + i.toString()).style.backgroundColor = "#6aaa64";
  //       document.getElementById(times.toString() + i.toString()).style.color = "#fff";
  //     } else {
  //       document.getElementById(times.toString() + i.toString()).style.backgroundColor = "#c9b458";
  //       document.getElementById(times.toString() + i.toString()).style.color = "#fff";
  //     }
  //   }
  // });
  //fix  
  let green = puzzle;
  //先决定绿色，即完全正确字母，并替换
  tiles[times-1].forEach((letter, i) => {
    if (puzzle[i] == letter.toLowerCase()) {
      document.getElementById(times.toString() + i.toString()).style.backgroundColor = "#6aaa64";
      document.getElementById(times.toString() + i.toString()).style.color = "#fff";
      green = green.substring(0, i) + " " + green.substring(i + 1); //替换字母
    } 
  });
  //再决定黄色和灰色
  let yellow = green;
  tiles[times-1].forEach((letter, i) => {
    if ( green[i] != " ") {
      const j = yellow.indexOf(letter.toLowerCase());
      if (j == -1) {
        document.getElementById(times.toString() + i.toString()).style.backgroundColor = "#787c7e";
        document.getElementById(times.toString() + i.toString()).style.color = "#fff";
        document.getElementById(letter).style.backgroundColor = "#787c7e";
        document.getElementById(letter).style.color = "#fff";
      } else {
        document.getElementById(times.toString() + i.toString()).style.backgroundColor = "#c9b458";
        document.getElementById(times.toString() + i.toString()).style.color = "#fff";
        yellow = yellow.substring(0, j) + " " + yellow.substring(j + 1); //替换字母
      }
    }
  });
  
 
  if (times == chance) {
    alert("bad luck! the answer is '" + puzzle + "'");
    return;
  }
  times += 1;
  index = 0;
};

window.handleSurrenderClick = (event) => {
  alert("the answer is '" + puzzle + "'");
};

window.handleRestartClick = (event) => {
  random = Math.floor(Math.random() * (words.length - 1));
  puzzle = words[random];  //生成随机单词
  console.log(puzzle);
  times = 1;  //当前第几次猜，也对应第几行，times-1对应tiles数组前一维度的下标
  index = 0;  //一行对应中第几个格子，对应tiles数组后一维度的下标
  tiles = []; //对应填写格，二维数组
  got = false; //是否猜对
  
  //reset grid
  for (let i = 1; i <= chance; i++) {
    for (let j = 0; j < wordle; j++) {
      document.getElementById(i.toString() + j.toString()).style.backgroundColor = "#fff";
      document.getElementById(i.toString() + j.toString()).style.color = "#000";
      document.getElementById(i.toString() + j.toString()).innerText = "";
    }
  }

  //reset keyboard
  for (letter of "QWERTYUIOPASDFGHJKLZXCVBNM") {
    document.getElementById(letter).style.backgroundColor = "#d3d6da";
    document.getElementById(letter).style.color = "#000";
  }
}

document.onkeydown = function(event) {
  if (event.keyCode >= 65 && event.keyCode <= 90) {  //A-Z
    const letter = event.key.toUpperCase();
    window.handleLetterClick({
      target: {
        innerText: letter
      }
    });
  } else if (event.keyCode == 13) { //Enter
    window.handleGuessClick();
  } else if (event.keyCode == 8) { //Backspace
    window.handleBackClick();
  } 
}

