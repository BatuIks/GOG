#zad1
#proszę stworzyć macierz o wymiarach 50 na 4 składającą się z liczb z przedziału od -1 do 1
x = runif(200, -1, 1)
m = matrix(x , nrow = 50, ncol = 4)

#zad2
#prosze zamienic miejscami dwie kolumny w stworzonej macierzy
q = m[,1]
m[,1] = m[,2]
m[,2] = q

m[,c(1,2)]<
#zad3
#prosze zamienic wartosci jednej kolumn na sinusy tych wartosci
m[,2] = sin(m[,2])

#zad4
#proszę zamienic macierz na data frame oraz nazwać kolumny
data1 = data.frame(m)
colnames(data1) <- c("col1", "col2", "col3", "col4")

#zad5
#prosze stworzyc dodatkową kolumnę będącą sumą wartości dla każdego wiersza
m1 = cbind(m, apply(m, 1, sum))

#ewentualnie
m1$sumy<-rawSums(m1)
#zad6
#prosze stworzyc nowy obiekt składający się z tych wierszy początkowej macierzy, w których 
#wartość w pierwszej kolumnie jest większa od zera
nowaM = matrix(, nrow = length(m[1,]), ncol = 0)

for(i in 1:length(m[,1])){
  if(m[i,1] > 0){
    nowaM = rbind(nowaM,m[i,])
  }
}

m2 <- m1[which(m[])]