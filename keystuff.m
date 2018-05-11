function keystuff
    figure('units','pixels',...
        'position',[500 500 200 260],...
        'keypressfcn',@fh_kpfcn);
    uicontrol('style','text',...
        'units','pixels',...
        'position',[60 120 80 20],...
        'fontweight','bold',...
        'fontsize',8); 
end

function [] = fh_kpfcn(H,E)
    P = H.Position;
    txP = H.Children.Position;
    txS = H.Children.FontSize;
    H.Children.String = E.Key;
    if ~any(P(3:4) < [200 260])
        switch E.Key
            case {'rightarrow','d'}
                H.Position = P + [5 0 0 0];
            case {'leftarrow','a'}
                H.Position = P + [-5 0 0 0];
            case {'uparrow','w'}
                H.Position = P + [0 5 0 0];
            case {'downarrow','s'}
                H.Position = P + [0 -5 0 0];
            case 'q'
                H.Position = P + [-10 -10 20 20];
                H.Children.Position = txP + [5 5 10 10];
                H.Children.FontSize = txS + 1;
            case 'e'
                H.Position = P + [10 10 -20 -20];
                H.Children.Position = txP + [-5 -5 -10 -10];
                H.Children.FontSize = txS - 1;
            case 'space'
                H.Position = [P(1:2) 200 260];
                H.Children.Position = [60 120 80 20];
                H.Children.FontSize = 10;
            case 'escape'
                close(H);
        end
    else
        switch E.Key
            case {'rightarrow','d'}
                H.Position = P + [5 0 0 0];
            case {'leftarrow','a'}
                H.Position = P + [-5 0 0 0];
            case {'uparrow','w'}
                H.Position = P + [0 5 0 0];
            case {'downarrow','s'}
                H.Position = P + [0 -5 0 0];
            case 'q'
                H.Position = P + [-10 -10 20 20];
                H.Children.Position = txP + [5 5 10 10];
                H.Children.FontSize = txS + 1;
            case 'space'
                H.Position = [P(1:2) 200 260];
                H.Children.Position = [60 120 80 20];
                H.Children.FontSize = 10;
            case 'escape'
                close(H);
        end
    end
end