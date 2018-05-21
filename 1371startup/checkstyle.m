function checkstyle(varargin)
    global nS
    nS = false;
    if any(strcmp(varargin,'-ns'))
        nS = true;
        varargin(strcmp(varargin,'-ns')) = [];
    end
    for i = 1:length(varargin)
        if exist(varargin{i},'file') == 2
            if contains(varargin{i},'.m')
                fprintf('\nChecking %s:\n',varargin{i})
                checkFileStyle(varargin{i})
            else
                fprintf('\nChecking %s.m:\n',varargin{i})
                checkFileStyle([varargin{i} '.m'])
            end
        else
            fprintf('\nFunction %s does not exist in your current directory.\n',varargin{i})
        end
    end
end

function checkFileStyle(filename)
    global variables lineNum
    lineNum = 0;
    ctrlStructs = {};
    variables = {};
    fh = fopen(filename);
    line = fgetl(fh);
    lineNum = lineNum + 1;
    if ~contains(line,'function')
        fprintf('\tThe function header should be the first line of the file.\n')
    end
    while ischar(line)
        % Function header first
        % Normal line style
        % switch style
        % if style
        % for style
        % while style
        % ignore comments unless it is shutting checkstyle up
        if strcmp(strtrim(line),'end')
            ctrlStructs(end) = [];
        end
        checkIndentation
        if contains(line,{'function','if','switch','case','otherwise','for','while'})
            ctrlStructs = [ctrlStructs {'hi'}];
        end

        if contains(line,'function') && contains(line,strtok(filename,'.'))
            %isValidFcnHeader(line)





        end

        line = fgetl(fh);
        lineNum = lineNum + 1;
    end
    fclose(fh);
    function checkIndentation
        if ~strcmp(line(1:4*length(ctrlStructs)),char(32.*ones(1,4.*length(ctrlStructs)))) && line(4*length(ctrlStructs)+1) ~= ' '
            inds = find(line == ' ') - 1;
            if ~isempty(inds)
                fprintf('\tIndentation on line %d is %d. It should be %d\n',lineNum,inds(1),4*length(ctrlStucts))
            end
        end
    end
end



fcnHeaderRegEx = '^\s+function((\s+[a-zA-Z0-9_]*|\s*\[\s*[a-zA-Z0-9_ ,]*\s*\])\s*=)?\s+[a-zA-z][a-zA-Z0-9_]*\s*(\(\s*[a-zA-Z0-9_ ,]*\s*\))?$';
%                     function     var1                 [var2]                 =      fcnName                    (var3)
