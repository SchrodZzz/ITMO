* S -> TN`(`A`);`

***

* T -> `Name` R
    * R -> `[]`R
    * R -> eps

***

* N -> Z
    * Z -> `*`Z
    * Z -> `&`M
        * M -> `Name`eps
    * Z -> `Name`eps

***

* A -> TNB
    * B -> `,`TNB