package client.Commands;

import client.Readers.WorkerReader;
import client.UDPClient;
import common.Constants;
import common.Exceptions.WrongAmountOfArgumentsException;
import common.UserCommand;
import common.requests.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Class with realization of filter_less_than_end_date command
 * <p>This command is used to print all elements whose endDate is less than given
 * @see UserCommand
 */
public class FilterLessThanEndDateCommand extends UserCommand {
    private UDPClient client;
    /**
     * Worker reader which is used to read endDate
     */
    private WorkerReader workerReader;

    /**
     * FilterLessThanEndDateCommand constructor
     * <p> Firstly it initializes super constructor by command name, arguments and description
     * @param workerReader
     */
    public FilterLessThanEndDateCommand(WorkerReader workerReader, UDPClient client) {
        super("filter_less_than_end_date", "endDate", "print all elements whose endDate is less than given");
        this.workerReader = workerReader;
        this.client = client;
    }

    /**
     * Method to complete filter_less_than_end_date command
     * <p>It reads endDate from user and then print all elements whose endDate is less than given
     * <p>If collection is empty endDate is not read (except script mode)
     *
     */
    @Override
    public ExecuteCommandResponce execute() {
        try {
            client.sendObject(new ClientRequest(ClientRequestType.IS_COLLECTION_EMPTY, null));
            if (((boolean)client.receiveObject())) {
                if (Constants.SCRIPT_MODE) {
                    workerReader.readEndDate();
                }
                return new ExecuteCommandResponce(ResultState.SUCCESS, "Collection is empty!");
            }
            LocalDateTime endDate = workerReader.readEndDate();
            ArrayList<Serializable> arguments = new ArrayList<>();
            arguments.add(endDate);
            client.sendObject(new ClientRequest(ClientRequestType.EXECUTE_COMMAND, new ExecuteCommandRequest(super.getName(), arguments)));
            return (ExecuteCommandResponce) client.receiveObject();
        } catch (Exception e){
            return new ExecuteCommandResponce(ResultState.EXCEPTION, e);
        }
    }

    /**
     * Method checks if amount arguments is correct
     *
     * @param arguments String array with different arguments
     * @throws WrongAmountOfArgumentsException If number of arguments is not equal to zero
     */
    @Override
    public void initCommandArgs(ArrayList<Serializable> arguments) throws WrongAmountOfArgumentsException {
        if(!arguments.isEmpty()) throw new WrongAmountOfArgumentsException("Wrong amount of arguments!", 0, arguments.size());
    }
}