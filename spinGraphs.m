function spinGraphs(terms,step)
    rho = fib(100);
    theta = 0:pi/step:(100-1).*pi/step;
    rho = interp1(theta,rho,linspace(1,2),'spline','extrap');
    polarplot(theta,rho,'m--')
end

function out = fib(terms)
    out = [1 1];
    while length(out) < terms
        out = [out out(end)+out(end-1)];
    end
end