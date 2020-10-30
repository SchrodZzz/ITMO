* S -> TN`(`A`);`

***

* T -> `Name+` eps

***

* N -> Z
    * Z -> `*`Z
    * Z -> `&`M
        * M -> `Name`eps
    * Z -> `Name`eps

***

* A -> TNB
    * B -> `,`TNB