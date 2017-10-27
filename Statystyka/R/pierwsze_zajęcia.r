x = 5
y = c(2,6,7)
z = c(4,5,9)
z+y
z-y
z*y
z/y
wektor = c("Daniel", "nie odmawia", "pacierza", "przed","snem")
wektor2 = c(1,43,63,26)
#nie rozumiem
wektor2 = as.factor(wektor2)
#nie rozumiem
wektor2 = as.numeric(wektor2)
x = 1:9
tab = matrix(x, nrow = 3, ncol = 3, byrow = TRUE)
tab
x = c(2,3,"haafg")
tab = matrix(x, nrow = 3, ncol = 3, byrow = TRUE)
tab
tab2 = data.frame(y,x,c("płeć","wiek","wykształcenie"))
tab2
colnames(tab2)
colnames(tab2) <- c("label1", "label2", "label3")
colnames(tab2)
tab2
x <- c(2,3,4)
x[3]
tab[,c(2,3)]
tab[c(1,2),]
tab2[,c("label3")]
tab2$label1
tab2$label2
tab[3,"label2"]
w <- which(tab2$label2 == 3)
