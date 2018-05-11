function slopes(input,axvec,step)
    %sets the length of the line so that they do not cross eachother
    len = 1/(2*step);
    %creates the grids of x and y values to be plugged in later
    [X,Y] = meshgrid(linspace(axvec(1),axvec(2),step*(axvec(2)-axvec(1))+1),linspace(axvec(3),axvec(4),step*(axvec(2)-axvec(1))+1));
    [rows,cols] = size(X);
    %these loops create the values of xprime and yprime for each slope
    for r = 1:rows
        for c = 1:cols
            [xval,yval] = equation(input,X(r,c),Y(r,c));
            xprime(r,c) = xval;
            yprime(r,c) = yval;
            magnitude(r,c) = sqrt(xval^2 + yval^2);
        end
    end
    %asthetic. Makes the small slopes more visible.
    magnitude = (magnitude./(max(max(magnitude)))).^(.25);
    %values set for a 1080p screen, may need to be changed for other monitors
    figure('units','pixels','outerposition',[200,25,1100,850]);
    hold on
    %creates an angle using xprime and yprime at points, then plots an
    %arrow. Probably could have been done with the quiver command, but this
    %was a learning experience for me.
    for r = 1:rows
        for c = 1:cols
            theta = atan(yprime(r,c)/xprime(r,c));
            x = [X(r,c)-magnitude(r,c)*len*cos(theta), X(r,c), X(r,c)+magnitude(r,c)*len*cos(theta)];
            y = [Y(r,c)-magnitude(r,c)*len*sin(theta), Y(r,c), Y(r,c)+magnitude(r,c)*len*sin(theta)];
            plot(x,y,'k-');
            [ax,ay] = arrow(xprime(r,c),yprime(r,c),theta,X(r,c),Y(r,c),magnitude(r,c)*len);
            plot(ax,ay,'k-');
            if xprime(r,c) == 0 && yprime(r,c) == 0
                plot(X(r,c),Y(r,c),'k*'); %crit points that exist on the grid show up as a star
            end
        end
    end
    %%%The rest of this focused on the creation of lines
    %the step and tolerance are mainly to help the computer know where to end the lines
    step = .001;
    tol = .0001;
    %creates starting points along the x axis and y axis, yes the (0,0) is
    %repeated, but I dont think it's worth removing the second.
    vec1 = axvec(1):axvec(2);
    vec2 = axvec(3):axvec(4);
    stpts = [vec1,zeros(1,length(vec2));zeros(1,length(vec1)),vec2];
    %user feedback so they can tell if the code has gotten to an endless loop
    fprintf('Loading %.2f%s\n',100*length(stpts)/(2*length(stpts)),'%')
    for k = 1:length(stpts)
        %Line stepping forward.
        cond = true;
        x = [stpts(1,k)];
        y = [stpts(2,k)];
        while cond
            [xprime,yprime] = equation(input,x(end),y(end));
            x = [x step*xprime+x(end)];
            y = [y step*yprime+y(end)];
            if (abs(xprime) < tol && abs(yprime) < tol) || (any(abs(x(1:end-1)-x(end)) < tol) && any(abs(y(1:end-1)-y(end)) < tol)) || (x(end) <= axvec(1) || x(end) >= axvec(2) || y(end) <= axvec(3) || y(end) >= axvec(4))
                cond = false;
            end
        end
        plot(x,y,'r-');
        
        %Line stepping backward
        cond = true;
        x = [stpts(1,k)];
        y = [stpts(2,k)];
        while cond
            [xprime,yprime] = equation(input,x(end),y(end));
            x = [x x(end)-step*xprime];
            y = [y y(end)-step*yprime];
            if (abs(xprime) < tol && abs(yprime) < tol) || (any(abs(x(1:end-1)-x(end)) < tol) && any(abs(y(1:end-1)-y(end)) < tol)) || (x(end) <= axvec(1) || x(end) >= axvec(2) || y(end) <= axvec(3) || y(end) >= axvec(4))
                cond = false;
            end
        end
        %user feedback
        fprintf('Loading %.2f%s\n',100*(k+length(stpts))/(2*length(stpts)),'%');
        plot(x,y,'r-');
    end
    hold off
    axis(axvec)
end

function [xprime,yprime] = equation(input,x,y)
    if isnumeric(input)
        [dim1,dim2] = size(input);
        if dim1 == 2 && dim2 == 2
            xprime = input(1,1)*x + input(1,2)*y;
            yprime = input(2,1)*x + input(2,2)*y;
        elseif dim1 == 2 && dim2 == 3
            xprime = input(1,1)*x + input(1,2)*y + input(1,3)*x*y;
            yprime = input(2,1)*x + input(2,2)*y + input(2,3)*x*y;
        elseif dim1 == 2 && dim2 == 4
            xprime = input(1,1)*x + input(1,2)*y + input(1,3)*x^2 + input(1,4)*y^2;
            yprime = input(2,1)*x + input(2,2)*y + input(2,3)*x^2 + input(2,4)*y^2;
        else
            xprime = x + 4*y - (y^2)*x;
            yprime = 2*x - y + (x^2)*y;
        end
    elseif iscell(input)
        [~,yp] = strtok(input{1},'=');
        [~,xp] = strtok(input{2},'=');
        yp = yp(2:end);
        xp = xp(2:end);
        yprime = eval(yp);
        xprime = eval(xp);
        
    end
end

function [ax,ay] = arrow(xprime,yprime,theta,X,Y,len)
    ax = [NaN,NaN,NaN]; ay = [NaN,NaN,NaN];
    if xprime > 0
        ax(2) = X+len*cos(theta);
        ay(2) = Y+len*sin(theta);
        ax(1) = ax(2)+len*cos(theta+5*pi/6);
        ax(3) = ax(2)+len*cos(theta-5*pi/6);
        ay(1) = ay(2)+len*sin(theta+5*pi/6);
        ay(3) = ay(2)+len*sin(theta-5*pi/6);
    elseif xprime < 0
        ax(2) = X-len*cos(theta);
        ay(2) = Y-len*sin(theta);
        ax(1) = ax(2)-len*cos(theta+5*pi/6);
        ax(3) = ax(2)-len*cos(theta-5*pi/6);
        ay(1) = ay(2)-len*sin(theta+5*pi/6);
        ay(3) = ay(2)-len*sin(theta-5*pi/6);
    elseif yprime ~= 0
        ax(2) = X;
        if yprime > 0
            ay(2) = Y+len*sin(theta);
        else
            ay(2) = Y+len*sin(theta);
        end
        ax(1) = ax(2)+len*cos(theta+5*pi/6);
        ax(3) = ax(2)+len*cos(theta-5*pi/6);
        ay(1) = ay(2)+len*sin(theta+5*pi/6);
        ay(3) = ay(2)+len*sin(theta-5*pi/6);
    end
end

% sdickey5@jccc.edu