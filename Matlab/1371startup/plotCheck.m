function [log, feedback] = plotCheck(funcName,varargin)
    close('all')
    set(0,'DefaultFigureVisible','off')
    C = onCleanup(@(x) set(0,'DefaultFigureVisible','on'));
    feedback = {};
    stdn = getPlot(str2func(funcName),varargin{:});
    soln = getPlot(str2func([funcName '_soln']),varargin{:});
    if stdn.NumPlots ~= soln.NumPlots
        feedback = [feedback; {'Incorrect Number of Plots'}];
    end
    if ~all(ismember(stdn.Titles,soln.Titles))
        feedback = [feedback; {'Check your Chart Titles'}];
    end
    if ~all(ismember(stdn.XLabels,soln.XLabels))
        feedback = [feedback; {'Check your X-axis Labels'}];
    end
    if ~all(ismember(stdn.YLabels,soln.YLabels))
        feedback = [feedback; {'Check your Y-axis Labels'}];
    end
    diff = sum(sum(sum(stdn.Picture ~= soln.Picture)))/numel(soln.Picture);
    log = diff <= .005;
    if ~log && isequal(size(stdn.Picture),size(soln.Picture))
        fig = figure();
        imshow(~any(stdn.Picture ~= soln.Picture,3))
        title Differences
        fig.Visible = 'on';
        feedback = [feedback; {'The plots are different at the BLACK pixels'}];
    else
        feedback = {'All correct'};
    end
end

function data = getPlot(fcnHandle,varargin)
    fcnHandle(varargin{:});
    fig = gcf;
    frame = getframe(gcf);
    data.Picture = frame.cdata;
    data.NumPlots = length(fig.Children);
    data.Positions = {fig.Children.Position};
    data.XLims = {fig.Children.XLim};
    data.YLims = {fig.Children.YLim};
    data.Titles = cell(1,data.NumPlots);
    data.XLabels = cell(1,data.NumPlots);
    data.YLabels = cell(1,data.NumPlots);
    for i = 1:data.NumPlots
        data.Titles{i} = fig.Children(i).Title.String;
        data.XLabels{i} = fig.Children(i).XLabel.String;
        data.YLabels{i} = fig.Children(i).YLabel.String;
        data.Plots(i).OnPlot = arrayfun(@class,fig.Children(i).Children(:),'UniformOutput',false);
    end
    
    
    close(fig)
end