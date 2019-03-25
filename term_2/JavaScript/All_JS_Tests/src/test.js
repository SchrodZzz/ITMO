/**--------------------------- tests zone ----------------------------**/

// println(parse("x x 2 - * x * 1 +")(5) === 76 ? "ok" : "oops");
//
// let f1 = () => {return "1_first"};
// println(f1());
// f1 = () => {return "1_second"};
// println(f1);
//
// function f2 () {return "2_first"}
// println(f2());
// function f2() {return"2_second"}
//
// let printer = (obj) => {println(obj)};
// let printCnsts = () => printer(Object.keys(CNSTS));
// printCnsts();

// let expr = add(
//     subtract(
//         multiply(
//             variable("x"),
//             variable("x")
//         ),
//         multiply(
//             cnst(2),
//             variable("x")
//         )
//     ),
//     cnst(1)
// );
//
// for (let i = 0; i < 10; i++) {
//     let result = expr(i);
//     let expResult = i * i - 2 * i + 1;
//     println("i = " + i + " : " + result + " " + ((result === expResult) ? "ok" : "oops - expected : " + expResult));
// }