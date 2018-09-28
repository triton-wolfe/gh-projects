w = waitbar(0,'My Progress Bar');                       % create a new waitbar, w with 0% progress
for i=1:500
   isprime(i);
   w = waitbar(i/500,w,['iteration: ',num2str(i)]);     % update the wait bar each iteration
end
close(w)