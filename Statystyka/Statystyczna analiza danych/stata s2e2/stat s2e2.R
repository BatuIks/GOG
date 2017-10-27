install.packages("MASS")

ucv(geyser$duration) 

bcv(geyser$duration) 
width.SJ(geyser$duration)
width.SJ(geyser$duration, method = "dpi")

d <- density(geyser$duration, ucv(geyser$duration)) 
plot(d)
library(MASS)
setwd("~/Pobrane")
load("cw2.RData")

data <- geyser$duration

compareDensEst <- function(data, kernel = kernel){
  par(mfrow = c(2,2))
  d <- density(data, bw = ucv(data), kernel = kernel) 
  plot(d, main = "ucv")
  d <- density(data, bw = bcv(data), kernel = kernel)
  plot(d, main = "bcv")
  d <- density(data, bw = width.SJ(data), kernel = kernel)
  plot(d, main = "width.JS")
  d <- density(data, bw = width.SJ(data), method = "dpi", kernel = kernel)
  plot(d, main = "widht.JS + dpi")
}

compareDensEst
#zad1
compareDensEst(data, "gaussian")
#zad2
compareDensEst(hildago, "rectangular")
#zad3

reszta <- function(N){
  k <- sample(0:4, N, replace = TRUE)
  result <- rnorm(N, mean(k/2), sd =1)
  return(result)
}
reszta(5)
n <- 100000
ifelse(c(TRUE, FALSE, TRUE), c(1,2,3), c("x","y","z"))
mySample <- ifelse(runif(n)<.5,rnorm(n), reszta(n))

compareDensEst(mySample, "gaussian")

#zad4
compareDensEst(ushighways$Approx.Miles, "rectangular")