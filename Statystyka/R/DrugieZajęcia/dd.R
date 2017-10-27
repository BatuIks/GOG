macierz = matrix(1:12,3,4)

zamiana = function(macierz){
  a = macierz[1,1]
  b = macierz[length(macierz[,1]),length(macierz[1,])]

  macierz[length(macierz[,1]),length(macierz[1,])] = a
  macierz[1,1] = b
  return(macierz)
}

b = macierz[length(macierz[,1]),length(macierz[1,])]

c = zamiana(tab)
dim(tab)

mac = matrix(1:15, nrow = 5)


srednie = 1:5
for(i in 1:5){
  srednie[i] = mean(mac[i,])
}
sred = srednie


?apply()


apply(mac, 2, mean) #apply(macierz, 1 - po wierszach, 2 - kolumnach, funkcja na nich stosowa)

colMeans(mac)
rowMeans(mac)
?rnorm #generuje n elementów rozkładu normalnego

x = rnorm(100,10,2)
y = x + rnorm(100)

?plot() #wykresik

plot(x,y)
tabela = data.frame(x,y)

cor(tab)

library("Hmisc")
rcorr(as.matrix(tab))
#ggplot - pakiet do ogarnięcia

x <- rnorm(100,sd = 3)
y <- x^2 + rnorm(100)
plot(x,y)
cor(x,y)

library("Hmisc")
rcorr(x,y)